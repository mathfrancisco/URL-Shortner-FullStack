# 🚀 Encurtador de URL
API de Encurtamento de URL baseada em Java criada para gerenciamento de URLs eficiente.

## 👨‍💻 Principais Tecnologias
- **Java 17**: Utilizado a versão LTS mais recente do Java para aproveitar suas características robustas e inovações;
- **Spring Boot com Maven**: Construído com o popular framework Spring Boot, utilizando Maven para gerenciamento de projetos eficiente;
- **Angular 16**: Criado uma interface de usuário amigável com Angular 16, fornecendo uma interface intuitiva para os usuários encurtarem URLs;
- **Docker**: Containerizou a aplicação usando Docker, garantindo implantação e gerenciamento fáceis;
- **MySQL**: Utilizado um banco de dados MySQL para armazenar URLs encurtadas e seus links originais correspondentes;
- **Nginx**: Implementado Nginx como servidor proxy reverso para lidar com solicitações de entrada e melhorar o desempenho da aplicação;
- **AWS EC2**: Implantou a aplicação na AWS EC2, fornecendo uma infraestrutura escalável e segura.


##  🤖 Como funciona:
- **Os usuários inserem a URL original no formulário.**
- **O backend gera uma URL encurtada e a exibe abaixo do formulário com opção de copiar.**
- **A URL encurtada é armazenada no banco de dados MySQL.**
- **A aplicação roda em containers Docker, hospedada na AWS EC2, garantindo alta disponibilidade e escalabilidade.**
  
Este projeto de Encurtador de URL fornece uma experiência de encurtamento de URL sem problemas, aproveitando as fortes características do Java, Spring Boot, Angular e Docker, enquanto garante escalabilidade e segurança com AWS EC2 e Nginx.
