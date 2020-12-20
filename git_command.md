# git コマンドチートシート

リモートリポジトリは進んでいるとき
```
    git pull (origin [branch名])
```

編集終了時
```
    git add [ファイル名] // index(stage)に上げる
    git commit -m "コメント" // indexにあるものをコミットする
    git push (origin [branch名])
```

git add / rm について
```
    git add [ファイル名] // リモートリポジトリに追加する
    git rm  [ファイル名] // リモートリポジトリから削除する
    git rm --cached [ファイル名] // ローカルにだけ残して〃
```