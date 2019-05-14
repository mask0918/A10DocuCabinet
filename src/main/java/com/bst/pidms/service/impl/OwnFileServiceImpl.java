package com.bst.pidms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.dao.BindLabelFileMapper;
import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.docu.DocuInfo;
import com.bst.pidms.entity.picture.ExifReader;
import com.bst.pidms.entity.picture.GoogleVision;
import com.bst.pidms.entity.picture.PicInfo;
import com.bst.pidms.entity.reader.OfficeReader;
import com.bst.pidms.entity.video.*;
import com.bst.pidms.esmapper.EsFileMapper;
import com.bst.pidms.service.BindLabelFileService;
import com.bst.pidms.service.LabelService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.NLPUtils;
import com.bst.pidms.utils.SessionUtil;
import com.bst.pidms.utils.ThumbUtils;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @Author: BST
 * @Date: 2019/4/5 11:45
 */
@Slf4j
@Service
public class OwnFileServiceImpl implements OwnFileService {


    public static final String host = "http://yzny6c.natappfree.cc/";

    public static final String qiniuUrl = "http://ppxlrdgsm.bkt.clouddn.com/";

    @Resource
    DocumentConverter documentConverter;

    @Autowired
    EsFileMapper esFileMapper;

    @Autowired
    private OwnFileMapper ownFileMapper;

    @Autowired
    private LabelService labelService;

    @Autowired
    BindLabelFileMapper bindLabelFileMapper;

    @Autowired
    BindLabelFileService bindLabelFileService;

    @Override
    public void updateFile(OwnFile ownFile) {
        esFileMapper.save(ownFile);
        ownFileMapper.updateByPrimaryKeySelective(ownFile);
    }

    @Override
    public OwnFile getFileById(Integer id) {
        return ownFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addFile(OwnFile file) {
        ownFileMapper.insertSelective(file);
        esFileMapper.save(file);
    }

    @Override
    public void deleteFileById(Integer id) {
        ownFileMapper.deleteByPrimaryKey(id);
        esFileMapper.deleteById(id);
    }

    @Override
    public List<OwnFile> getCategory(Integer id, Integer userId) {
        return ownFileMapper.selectCategory(id, userId);
    }

    @Override
    public List<String> getTimeline(Integer userId) {
        return ownFileMapper.selectTimelineByUserId(userId);
    }

    @Override
    public List<OwnFile> getFileByTimeline(Integer userId, String date) {
        return ownFileMapper.selectFileByTimeline(userId, date);
    }

    @Override
    public List<OwnFile> getByCatalog(Integer id) {
        return ownFileMapper.selectByCatalogId(id);
    }

    @Override
    public List<OwnFile> getAll() {
        return ownFileMapper.selectAll();
    }

    @Override
    public List<OwnFile> getFilingInfo(Integer labelId) {
        List<Integer> integers = bindLabelFileMapper.selectFileIdByLabelId(labelId);
        List<OwnFile> ownFiles = ownFileMapper.selectForeachByIds(integers);
        return ownFiles;
    }

    @Override
    public List<OwnFile> getIfCollect(Integer userId) {
        return ownFileMapper.selectIfCollect(userId);
    }

    @Override
    public List<OwnFile> getIfAttenton(Integer userId) {
        return ownFileMapper.selectIfAttention(userId);
    }

    @Override
    public void setCollect(Integer id, Integer flag) {
        ownFileMapper.setCollectStatus(id, flag);
        esFileMapper.save(getFileById(id));
    }

    @Override
    public void setAttention(Integer id, Integer flag) {
        ownFileMapper.setAttentionStatus(id, flag);
        esFileMapper.save(getFileById(id));
    }

    @Override
    @Async
    public void getImageResults(OwnFile ownFile, File file, Integer idNumber) {
        List<String> topLabels = new ArrayList<>();
        try {
            PicInfo picInfo = ExifReader.readEXIF(file);
            if (picInfo.getCreateTime() != null) {
                ownFile.setUploadTime(picInfo.getCreateTime());
            }
            Map<String, Object> fileMap = GoogleVision.Vision_Java(file);
            picInfo.setColors((List<PicInfo.Color>) fileMap.get("colors"));
            picInfo.setOcrInfo((String) fileMap.get("ocr"));
            List<String> labels = (List<String>) fileMap.get("labels");
            for (String s : labels) {
                // 默认为3个标签
                if (labels.indexOf(s) > 5) break;
                topLabels.add(s);
            }
            ownFile.setKeyword(Joiner.on("|").join(labels));
            ownFile.setInfo(JSONObject.toJSONString(picInfo));
        } catch (Exception e) {
            e.getMessage();
        } finally {
            ownFileMapper.updateByPrimaryKeySelective(ownFile);
            esFileMapper.save(ownFile);
            for (String topLabel : topLabels) {
                // 默认UserID=1;
                Integer id = labelService.addLabelIfNotExist(idNumber, topLabel, true);
                bindLabelFileService.addBind(id, ownFile.getId(), ownFile.getCategory());
            }
        }
    }

    @Override
    public void getAudioResults(OwnFile ownFile, File file) {
        // TODO: 2019/4/18
    }

    @Override
    public void getVideoResults(OwnFile ownFile, File file, Integer idNumber) {
        List<String> topLabels = new ArrayList<>();
        String fileName = ownFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        try {
            VideoInfo videoInfo = VideoReader.getInfo(file.getAbsolutePath(), "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe");

            File thumbFile = new File(file.getParent() + "\\THUMB");
            if (!thumbFile.exists()) thumbFile.mkdir();
            String thumbAbsName = thumbFile.getAbsolutePath() + "\\" + fileName + "_tmp.png";
            String thumbName = ownFile.getUrl().substring(0, ownFile.getUrl().lastIndexOf("/") + 1) + "THUMB/" + fileName + "_tmp.png";
            ThumbUtils.getThumb(file.getAbsolutePath(), thumbAbsName, videoInfo.getWidth(), videoInfo.getHeight(), 0, 0, Math.random() * videoInfo.getDuration());
            videoInfo.setThumbUrl(thumbName);

            VideoResults videoResults = GoogleVideo.Video_Java(file.getAbsolutePath());
            videoInfo.setVideoResults(videoResults);
            Set set = new HashSet();
            Set themeSet = new HashSet();
            for (VideoLabel videoLabel : videoResults.getVideoLabels()) {
                String videolabel = videoLabel.getVideolabel();
                if (!set.contains(videolabel)) {
                    set.add(videolabel);
                    themeSet.add(videolabel);
                }
            }
            for (ShotLabel shotLabel : videoResults.getShotLabels()) {
                String shotlabel = shotLabel.getShotlabel();
                if (!set.contains(shotlabel)) set.add(shotlabel);
            }
            int temp1 = 0;
            for (Object o : themeSet) {
                // 默认为3个标签
                if (temp1 > 5) break;
                topLabels.add((String) o);
                temp1++;
            }
            themeSet.add(suffix);
            ownFile.setKeyword(Joiner.on("|").join(themeSet));
            NLPUtils.getKeywords(host + "wordcloud", Joiner.on(" ").join(set), prefix + "_wordcloud");
            // 词云图片
            videoInfo.setWordCloudUrl(qiniuUrl + prefix + "_wordcloud.png");
            ownFile.setInfo(JSONObject.toJSONString(videoInfo));
        } catch (Exception e) {
            e.getMessage();
        } finally {
            ownFileMapper.updateByPrimaryKeySelective(ownFile);
            esFileMapper.save(ownFile);
            for (String topLabel : topLabels) {
                // 默认UserID=1;
                Label temp = new Label();
                Integer id = labelService.addLabelIfNotExist(idNumber, topLabel, true);
                bindLabelFileService.addBind(id, ownFile.getId(), ownFile.getCategory());
            }
        }
    }

    @Override
    @Async
    public void getDocumentResults(OwnFile ownFile, File file, Integer idNumber) {
        List<String> topLabels = new ArrayList<>();
        String fileName = ownFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        try {
            String info = OfficeReader.getInfo(file.getAbsolutePath(), suffix);
            String keywords = NLPUtils.getKeywords(host + "keywords", info, prefix);
            String[] split = keywords.split("[|]");
            int temp = 0;
            for (String s : split) {
                // 默认为3个标签
                if (temp > 4) break;
                topLabels.add(s);
                temp++;
            }
            ownFile.setKeyword(keywords);
            DocuInfo docuInfo = new DocuInfo();
            docuInfo.setInfo(info);
            if (Arrays.asList("doc", "docx", "xls", "xlsx", "ppt", "pptx").contains(suffix)) {
                documentConverter.convert(file).to(new File(file.getParent() + "\\" + prefix + ".pdf")).execute();
            }
            // 词云图片
            docuInfo.setWordCloudUrl(qiniuUrl + prefix + ".png");
            ownFile.setInfo(JSONObject.toJSONString(docuInfo));
        } catch (Exception e) {
            e.getMessage();
        } finally {
            ownFileMapper.updateByPrimaryKeySelective(ownFile);
            esFileMapper.save(ownFile);
            for (String topLabel : topLabels) {
                // 默认UserID=1;
                Integer id = labelService.addLabelIfNotExist(idNumber, topLabel, true);
                bindLabelFileService.addBind(id, ownFile.getId(), ownFile.getCategory());
            }
        }
    }

    @Override
    public void getOtherResults(OwnFile ownFile, File file) {
        // TODO: 2019/4/20
    }

}
