# class ModelObservable
詳しくはソースコードを見てください
## フィールドの説明
フィールドはすべてprivateなので必要なときはgetterまたはsetterを用いる。必要のないgetter・setterは実装していない。
- ```Bird bird``` Birdクラスのインスタンス
- ```ArrayList<Dokan> upperDokan``` Dokanクラスのインスタンスの配列
- ```ArrayList<Dokan> lowerDokan``` Dokanクラスのインスタンスの配列
- ```double t``` クリックされてからの経過時間
- ```boolean startFlag``` \
  falseの間は鳥・土管の位置が更新されない（すなわちゲームが止まった状態）。実際にはゲーム起動時とゲームオーバー時にfalseでそれ以外はtrueにする。
- ```boolean gameOverFlag``` \
  ゲームオーバ時にtrueになる、その他ではfalse
- ```boolean scoreFlag``` \
  スコアが更新されたときに

## publicメソッドと定数の説明(外部のクラスから利用できる)
### 定数
- ```int SCREEN_WIDTH``` 画面の幅
- ```int SCREEN_HEIGHT``` 画面の高さ
- ```int DOKAN_BUF``` 同時に描写される土管の数

### メソッド
- ```void init()``` \
実行すると、鳥の位置、土管の位置、スコアなどが初期化される。
- ```void setT(double time)``` \
引数のtimeをフィールドtに代入する。（経過時間は鳥の位置計算に使っている）
- ```void setStartFlag()``` \
ゲーム起動時にいきなり始まらないように、最初に入力があるまではfalse