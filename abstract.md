# Modelのメソッドの説明
view、contで使いそうな部分だけ説明
## Birdクラス
### 定数
- `int BIRD_WIDTH, BIRD_HEIGHT` : 鳥のサイズ
### フィールド
- `double y0` : クリック時の鳥の位置
### publicメソッド
- `void setYasY0()` : 今のyをy0に代入する

## ModelObservableクラス
### 定数
- `int SCREEN_WIDTH, SCREEN_HEIGHT` : 画面サイズ
### フィールド
- `Bird bird` : 鳥
- `ArrayList<Dokan> upperDokan, lowerDokan` : 上側の土管と下側の土管をそれぞれ配列として記憶
- `double time` : クリックされてからの経過時間
- `boolean startFlag` : falseの間は鳥も土管も動かない
- `boolean gameOverFlag` : ゲームオーバーでtrueになる
```
具体的にはゲーム開始時
    startFlag == false, gameOverFlag == false
ゲーム開始で
    startFlag == true,  gameOverFlag == false
ゲームオーバーで
    startFlag == false, gameOverFlag == true
リトライで最初に戻る
```
- `int score` : 現在のスコア
### publicメソッド
- `void init()` : 鳥、土管、スコアの初期化（リトライで呼び出してほしい）
- `Bird getBird()` : Birdクラスを返す
- `ArrayList<Dokan> getUpperDokan()` : 上側の土管の配列を返す
- `ArrayList<Dokan> getLowerDokan()` : 同様
- `void setTime(double t)` : `t`を`time`に代入
- `void setStartFlag(boolean flag)` : `flag`を`startFlag`に代入
- `void flyBird()` : `gameoverFlag`が立ってなかったら`startFlag`を立てる。鳥の`y0`に`y`を代入。`time`に`0`を代入