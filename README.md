# Spring Boot 勤怠管理システム

このプロジェクトは Spring Boot と Thymeleaf を使用した勤怠管理（WEB勤怠）システムです。
ユーザー認証、セッション管理、月単位での勤怠データ管理、レスポンシブデザインなどを実装しています。

## プロジェクト構成

```
springboot-thymeleaf-demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/springbootdemo/
│   │   │   ├── SpringBootThymeleafDemoApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java          (初期データ設定)
│   │   │   ├── controller/
│   │   │   │   ├── IndexController.java          (ログイン、メイン)
│   │   │   │   └── AttendanceController.java     (勤怠管理)
│   │   │   ├── entity/
│   │   │   │   ├── User.java                     (ユーザー)
│   │   │   │   ├── AttendanceMonth.java          (月別勤怠)
│   │   │   │   └── AttendanceDetail.java         (日別勤怠)
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── AttendanceMonthRepository.java
│   │   │   └── service/
│   │   │       ├── UserService.java
│   │   │       └── AttendanceService.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html                    (ログイン画面)
│   │       │   ├── main.html                     (メインメニュー)
│   │       │   ├── layout/
│   │       │   │   └── base.html                 (共有レイアウト)
│   │       │   └── attendance/
│   │       │       ├── entry.html                (勤怠一覧)
│   │       │       └── edit.html                 (勤怠編集)
│   │       ├── static/
│   │       │   └── css/
│   │       │       ├── common.css                (共通スタイル)
│   │       │       ├── layout.css                (レイアウト)
│   │       │       ├── login.css                 (ログイン画面)
│   │       │       ├── main.css                  (メインメニュー)
│   │       │       ├── attendance.css            (勤怠一覧)
│   │       │       └── attendance-edit.css       (勤怠編集)
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/com/example/springbootdemo/
│           └── SpringBootThymeleafDemoApplicationTests.java
├── Docs/
│   ├── index.md                                  (ログイン画面設計)
│   ├── main.md                                   (メイン画面設計)
│   ├── attendance_entry.md                       (勤怠一覧設計)
│   └── attendance_entry_edit.md                  (勤怠編集設計)
├── pom.xml
├── .gitignore
└── README.md
```

## 必要な環境

- Java 17以上
- Maven 3.6以上

## セットアップ手順

### 1. 依存関係をダウンロード

```bash
mvn clean install
```

### 2. アプリケーションを実行

```bash
mvn spring-boot:run
```

または、JARファイルをビルドして実行：

```bash
mvn clean package
java -jar target/springboot-thymeleaf-demo-1.0.0.jar
```

## アクセス方法

アプリケーションを起動した後、ブラウザで以下のURLにアクセスしてください：

- **ログイン画面**: http://localhost:8080/
- user : testUser password : testUser01 

## ログイン情報

テストユーザーが自動生成されます：

| ユーザーID | パスワード | 部署 | 名前 |
|----------|-----------|------|------|
| testUser | testUser01 | △△部署 | 秋山　◇◇ |
| user2 | user2pass | 営業部 | 田中　太郎 |
| user3 | user3pass | 企画部 | 鈴木　花子 |
| user4 | user4pass | IT部 | 佐藤　次郎 |

## 主な機能

### 1. ログイン画面 (index.html)
- ユーザーID・パスワード認証
- BCrypt によるパスワード暗号化
- ログイン失敗時のエラー表示
- レスポンシブデザイン対応

### 2. メインメニュー (main.html)
- ログイン済みユーザー情報を表示（部署、名前、スタッフコード）
- グリッドレイアウトのメニューシステム
- WEB勤怠へのリンク
- Font Awesome アイコン対応

### 3. 勤怠一覧 (attendance/entry.html)
- 月単位での勤怠データ表示
- 曜日による背景色分け（平日：白、土曜：水色、日曜：薄赤）
- 前月・来月へのナビゲーション
- **当月以外はクリック不可** - 過去月・未来月はグレーアウト
- 区分、始業時間、終業時間、承認状況を表示

### 4. 勤怠編集 (attendance/edit.html)
- 勤務区分（通常、公休日、休出、法休出）選択
- 始業時間・終業時間入力（4桁の24時間形式：0900, 1800など）
- 休憩時間、深夜休憩時間入力
- 業務内容・就業場所の記入（最大300文字）
- フォーム検証（JavaScriptとサーバー側）
- 登録、キャンセル、削除ボタン
- 削除は**論理削除**（レコード保持、データリセット）
- レスポンシブデザイン対応

## セキュリティ機能

### 認証・セッション管理
- Spring Security Crypto による BCrypt パスワード暗号化
- HttpSession によるセッション管理
- **セッションタイムアウト**: 3分間操作がない場合は自動ログアウト
- 各エンドポイントでセッション確認（未認証ユーザーはログイン画面へリダイレクト）

### パスワード管理
- BCrypt により平文パスワードを暗号化して保存
- ログイン時に入力パスワードを暗号化値と検証

## データベース

### 使用データベース
- **H2 Database** - ファイルベースの組み込みデータベース
- 保存位置: `~/springboot-demo/h2db.mv.db`
- テーブル:
  - `users` - ユーザー情報（userId, password, userName, department等）
  - `attendance_months` - 月別勤怠（userId, year, month）
  - `attendance_details` - 日別勤怠（dayOfMonth, classification, startTime等）

### データベース初期化
- `data.sql` でテストユーザーを自動生成
- `DataInitializer` でテストユーザーのパスワードを暗号化して保存

## テンプレートレイアウト

### Thymeleaf Layout Dialect
- `layout/base.html` - 共有レイアウト（ヘッダー・フッター）
- 子テンプレートで `layout:decorate="~{layout/base}"` を使用して共通化

### ヘッダー表示
- 会社名
- ログイン中のユーザー情報（部署、名前、スタッフコード）
- ホームに戻るボタン、メインページリンク

### フッター表示
- 「こちらはテスト用のサイトです。」メッセージ

## CSSの構成

CSS ファイルは機能別に分割されており、メンテナンスが容易です：

- **common.css** - 全ページ共通のスタイル
- **layout.css** - ヘッダー・フッターのスタイル
- **login.css** - ログイン画面のスタイル（背景色 #f5f5f5、主色 #007bff）
- **main.css** - メインメニュー（グリッドレイアウト）
- **attendance.css** - 勤怠一覧（テーブル、月度ナビゲーション）
- **attendance-edit.css** - 勤怠編集フォーム

### レスポンシブデザイン
- **デスクトップ** (768px以上) - フル機能
- **タブレット** (481px～768px) - 最適化されたレイアウト
- **モバイル** (480px以下) - シングルカラムレイアウト

## バリデーション

### フロントエンド (JavaScript)
- 区分（必須）の確認
- 時間形式の検証（4桁、00～23時、00～59分）
- 区分による入力制限（通常以外は時間・休憩入力不可）
- 文字数カウント（remarks 最大300文字）

### サーバーサイド (Spring Boot)
- 区分、時間形式の二重検証
- 不正なデータの保存防止

## フォントと配色

- **フォント**: Segoe UI
- **背景色**: #f5f5f5（薄いグレー）
- **主色**: #007bff（青）
- **アクセント**: #ff9800（オレンジ）
- **エラー**: #dc3545（赤）

## GitHub登録時の注意事項

`.gitignore` で以下のファイル/フォルダは自動的に除外されます：

- `target/` - ビルド成果物
- `Docs/` - 内部設計書
- `.idea/`, `.vscode/` - IDE設定ファイル
- H2 データベースファイル
- `node_modules/` - npm依存関係
- `.env` - 環境変数ファイル

## トラブルシューティング

### ポート8080がすでに使用されている場合

`src/main/resources/application.properties` を編集してください：

```properties
server.port=8081
```

### H2 Console に接続できない場合

JDBC URL が正しいことを確認してください：

```
jdbc:h2:file:~/springboot-demo/h2db;MODE=MySQL;QUOTE_IDENTIFIER=TRUE
```

### セッションがすぐに切れる場合

`application.properties` でタイムアウト時間を調整：

```properties
server.servlet.session.timeout=5m
```

## 外部ライブラリ

このプロジェクトでは以下のライブラリを使用しています：

- **Spring Boot 3.3.0** - フレームワーク
- **Spring Data JPA** - ORM
- **Spring Security Crypto** - パスワード暗号化
- **Thymeleaf** - テンプレートエンジン
- **Thymeleaf Layout Dialect** - レイアウト共有
- **H2 Database** - 組み込みデータベース
- **Hibernate** - JPA 実装
- **Font Awesome** - アイコン (CDN経由)

## 開発時の便利機能

DevTools が有効化されているため、以下の機能が利用できます：

- **自動リロード**: リソースファイル（HTML、CSS、JSなど）を変更すると自動で再読み込み
- **高速アプリケーション再起動**: 開発時の変更反映が速い

## ビルド

```bash
# クリーンビルド
mvn clean compile

# テストの実行
mvn test

# パッケージング
mvn package

# インストール
mvn install
```

## 次のステップ

このプロジェクトをベースに、以下の機能を追加してみてください：

- 実データベースへの移行（PostgreSQL, MySQL等）
- メール通知機能
- PDF出力機能
- 勤怠データのエクスポート（CSV, Excel）
- 承認ワークフロー
- 複数ユーザーの勤怠集計
- Spring Security による高度な権限管理
- RESTful API の拡張
- API 仕様書の作成（OpenAPI/Swagger）

## ライセンス

MIT License

---

**最終更新**: 2026-06-28
