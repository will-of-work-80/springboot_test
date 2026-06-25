# Spring Boot + Thymeleaf Demo Project

このプロジェクトはSpring BootとThymeleafを使用したシンプルなWebアプリケーションです。
Thymeleafテンプレートエンジンを使用したレスポンシブなWebUIの実装例を示しています。

## プロジェクト構成

```
springboot-thymeleaf-demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/springbootdemo/
│   │   │   ├── SpringBootThymeleafDemoApplication.java
│   │   │   └── controller/
│   │   │       └── IndexController.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html          (ホームページ)
│   │       │   ├── hello.html          (グリーティングページ)
│   │       │   ├── about.html          (情報ページ)
│   │       │   └── main.html           (メインダッシュボード)
│   │       ├── static/
│   │       │   └── css/                (スタイルシート)
│   │       │       ├── common.css
│   │       │       ├── index.css
│   │       │       ├── hello.css
│   │       │       ├── about.css
│   │       │       └── main.css
│   │       └── application.properties
│   └── test/
│       └── java/com/example/springbootdemo/
│           └── SpringBootThymeleafDemoApplicationTests.java
├── pom.xml
├── .gitignore
└── README.md
```

## 必要な環境

- Java 17以上
- Maven 3.6以上

## セットアップ手順

### 1. Mavenをインストール

まだMavenをインストールしていない場合は、以下からダウンロードしてください：
https://maven.apache.org/download.cgi

### 2. 依存関係をダウンロード

```bash
mvn clean install
```

### 3. アプリケーションを実行

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

- **ホームページ**: http://localhost:8080/
- **Helloページ**: http://localhost:8080/hello
- **パラメータ付きHello**: http://localhost:8080/hello?name=YourName
- **Aboutページ**: http://localhost:8080/about
- **メインダッシュボード**: http://localhost:8080/main

## 主な機能

### 1. ホームページ (index.html)
- 動的なタイトルとメッセージ表示
- 現在時刻の表示
- リストアイテムの動的レンダリング
- ナビゲーションリンク

### 2. Helloページ (hello.html)
- クエリパラメータ（name）に基づく個人的なグリーティング
- グラデーション背景のレスポンシブデザイン
- スムーズなアニメーション効果

### 3. Aboutページ (about.html)
- プロジェクト情報の表示
- バージョン情報
- 機能リスト

### 4. メインダッシュボード (main.html)
- ヘッダーにログイン情報表示
- グリッドレイアウトのメニューシステム
- アクティブ/非アクティブなメニュー項目の表示
- Font Awesomeアイコン対応
- レスポンシブデザイン対応

## Thymeleafテンプレートの特徴

このプロジェクトでは、以下のThymeleaf機能を使用しています：

- **変数表示**: `th:text="${variable}"`
- **ループ処理**: `th:each`
- **URLリンク生成**: `th:href="@{/path}"`
- **日時フォーマット**: `th:text="${#temporals.format(...)}"`
- **条件分岐**: `th:if`, `th:unless` (必要に応じて追加可能)

## 開発中の便利機能

DevToolsが有効化されているため、以下の機能が利用できます：

- **自動リロード**: リソースファイル（HTML、CSS、JSなど）を変更すると自動で再読み込みされます
- **高速アプリケーション再起動**: 開発時の変更反映が速くなります

## ビルド

```bash
# クリーンビルド
mvn clean build

# テストの実行
mvn test

# パッケージング
mvn package

# インストール
mvn install
```

## トラブルシューティング

### ポート8080がすでに使用されている場合

`src/main/resources/application.properties`を編集して、ポート番号を変更してください：

```properties
server.port=8081
```

### テンプレートが見つからないエラーが出る場合

テンプレートファイルが`src/main/resources/templates/`ディレクトリに正しく配置されていることを確認してください。

## 外部ライブラリ

このプロジェクトでは以下のライブラリを使用しています：

- **Spring Boot 3.3.0** - フレームワーク
- **Thymeleaf** - テンプレートエンジン
- **Bootstrap / Font Awesome** - UIコンポーネント (CDN経由)

## CSSの構成

CSSファイルは機能別に分割されており、メンテナンスが容易です：

- **common.css** - 全ページ共通のスタイル
- **index.css** - ホームページのスタイル
- **hello.css** - グリーティングページのスタイル
- **about.css** - 情報ページのスタイル
- **main.css** - メインダッシュボードのスタイル

## GitHub登録時の注意事項

`.gitignore`で以下のファイル/フォルダは自動的に除外されます：

- `target/` - ビルド成果物
- `Docs/` - 内部設計書
- `.idea/`, `.vscode/` - IDE設定ファイル
- `node_modules/` - npm依存関係
- `.env` - 環境変数ファイル
- その他ログファイルなど

## 次のステップ

このプロジェクトをベースに、以下の機能を追加してみてください：

- データベース統合（Spring Data JPA）
- フォーム処理とバリデーション
- セッション管理
- セキュリティ（Spring Security）
- RESTful API
- API仕様書の作成（OpenAPI/Swagger）
- ロギング機能の強化

## ライセンス

MIT License

---

**最終更新**: 2026-06-25
