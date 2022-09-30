This project is a very basic app in Scala 3 + ZIO 2 + zio-http + Laminar.\
The backend uses zio-http + zio 2.\
The frontend uses Laminar + zio 2 + LaminarSAPUI5Bindings, allowing parallelism in a single-threaded web page (with nice UI5 web components).

The project is in itself totally useless, but is a good starting point for anyone interested in this amazing technology.

## 0. Get the required npms
In a console, in the "frontend" directory, type
```shell
npm install @ui5/webcomponents
npm install @ui5/webcomponents-fiori
npm install @ui5/webcomponents-icons
```

## I. Start the app

### 1. Start backend

#### - in a console (in the main directory, the one with the build.sbt file)

```shell
sbt "~backend / run"
```

### 2. Start frontend

#### - in another console (also in the main directory)

```shell
sbt
  ~ frontend / fastLinkJS / esBuild
```

### 3. Test the app

Then you can access the app at http://localhost:8090 \
click on one button or the other and see parallelism.
200 counters are displayed, and can be run independently. Each counter will stop counting 30 seconds after a start.
The start depends on the x position of the mouse.
The buttons and textarea are UI5 Components.


Note: The .gitignore file is a little bit unconventional, indicating what to keep and not what to ignore.

Why this project:\
1/ I love Scala, and especially Scala3 for indentation and other goodies.\
2/ I want the same (powerful) language on the server and on the client. It's simpler to manage, and far simpler for client/server communication.
3/ I also love ZIO, especially ZIO2. I obviously want a fast parallel server, but also a fast web page, running components in parallel.\
4/ I want standard components, and to be able to use them with my favourite "react-like" library, Laminar.




