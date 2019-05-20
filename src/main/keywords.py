# -*-coding:utf-8-*-
# import uniout  # 编码格式，解决中文输出乱码问题
import jieba.analyse
from sklearn import feature_extraction
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from wordcloud import WordCloud
import matplotlib.pyplot as plt
from qiniu import Auth, put_file, etag
from flask import Flask
import random
from flask import request
from wordcloud import ImageColorGenerator
from flask import render_template

app = Flask(__name__)

//api_id, api_key
q = Auth("", "")

"""
       TF-IDF权重：
           1、CountVectorizer 构建词频矩阵
           2、TfidfTransformer 构建tfidf权值计算
           3、文本的关键字
           4、对应的tfidf矩阵
"""

# jieba分词器通过词频获取关键词
def jieba_keywords(news, k=10):
    keywords = jieba.analyse.extract_tags(news, topK=k)
    print(keywords)
    return keywords


def qiniu(path):
    # 要上传的空间
    bucket_name = 'mask'
    # 上传后保存的文件名
    key = path
    # 生成上传 Token，可以指定过期时间等
    token = q.upload_token(bucket_name, key, 3600)
    # 要上传文件的本地路径
    localfile = './example.png'
    ret, info = put_file(token, key, localfile)
    print(info)
    assert ret['key'] == key
    assert ret['hash'] == etag(localfile)


@app.route("/keywords", methods=['POST'])
def get_content():
    content = request.args.get("content")
    title = request.args.get("title")
    # print(content)
    text = jieba_keywords(content)

    all_key = jieba_keywords(content, 50)
    fontpath = "/home/bst/音乐/msyhbd.ttc"
    my_wordcloud = WordCloud(background_color="white", width=500, height=360, margin=1, font_path=fontpath).generate(
        " ".join(all_key))
    plt.imshow(my_wordcloud)
    plt.axis("off")
    plt.savefig("example.png")
    qiniu(title + ".png")

    return "|".join(text)


@app.route('/wordcloud', methods=['POST'])
def get_wordcloud():

    content = request.args.get("content")
    print(content)

    title = request.args.get("title")
    fontpath = "/home/bst/音乐/msyhbd.ttc"
    # tfidf_keywords(news)
    my_wordcloud = WordCloud(background_color="white", width=500, height=360, margin=1, font_path=fontpath).generate(
        content)
    plt.imshow(my_wordcloud)
    plt.axis("off")
    plt.savefig("example.png")
    qiniu(title+".png")
    return "success"


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)
