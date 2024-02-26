## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

Server
javac -d bin src/sg/edu/nus/iss/baccarat/server/*
java -cp bin sg.edu.nus.iss.baccarat.server.ServerApp 12345 4
java -cp bin sg.edu.nus.iss.baccarat.server.Card

Client
javac -d bin src/sg/edu/nus/iss/baccarat/client/*
java -cp bin sg.edu.nus.iss.baccarat.client.ClientApp localhost:12345

ISSUES
1. HTML
