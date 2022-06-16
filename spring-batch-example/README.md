# SpringBatch 2022/6/7 バージョン

# 参考にしたサイト

https://qiita.com/kurukuruz/items/44743a6cd45037061548

# 起動の仕方

SpringBatchApplication.java を実行する。

# API の実行方法

http://localhost:8080/api/job/start/firstJob

- これで、Sample1Job が起動します。
- SampleJob は 30 秒スリープしています。非同期になれているっぽいので、ブラウザ側はすぐにレスポンスが返ってきます。
- ブラウザから 2 回ジョブをキックした場合、30 秒後に 2 回目が実行されます。

http://localhost:8080/api/job/start/secondJob

- これで、Sample2Job が起動します。
- firstJobとsecondJobの両方をキックした場合、それぞれで同実行1になります。
- 同時実行の設定はJob単位で有効で、全Jobを通した同時実行制御の方法はまだ分かっていません。

# h2-console

http://localhost:8080/h2-console

- User Name: sa
- Passoword: なし(空白)
