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

`git add` / `rm` について
```
    git add [ファイル名] // リモートリポジトリに追加する
    git rm  [ファイル名] // リモートリポジトリから削除する
    git rm --cached [ファイル名] // ローカルにだけ残して〃
```

`git fetch -p`
リモートに存在しなくなったリモート追跡ブランチを削除
```
$ git fetch -p
From https://github.com/kzbox/FlappyBird
 - [deleted]         (none)     -> origin/account_varify
 - [deleted]         (none)     -> origin/cont
 - [deleted]         (none)     -> origin/dev_cont1
 - [deleted]         (none)     -> origin/dev_cont2
 - [deleted]         (none)     -> origin/dev_model1
 - [deleted]         (none)     -> origin/dev_model2
 - [deleted]         (none)     -> origin/dev_model3
 - [deleted]         (none)     -> origin/dev_model5
 - [deleted]         (none)     -> origin/dev_start1
 - [deleted]         (none)     -> origin/dev_view1
 - [deleted]         (none)     -> origin/dev_view2
 - [deleted]         (none)     -> origin/model
 - [deleted]         (none)     -> origin/split_file2
 - [deleted]         (none)     -> origin/split_files
 - [deleted]         (none)     -> origin/view
 ```