#!/bin/bash
# Script de configuração e implantação para URL-Shortner-FullStack na EC2

# Variáveis
EC2_IP="18.117.143.115"
EC2_USER="ec2-user"
PEM_FILE="chaveum.pem"
PROJECT_DIR="URL-Shortner-FullStack"

# Função para exibir mensagens de status
log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1"
}

# 1. Configuração inicial
log_message "Iniciando configuração..."
chmod 400 $PEM_FILE

# 2. Criar estrutura de diretórios na instância EC2
log_message "Criando estrutura de diretórios..."
ssh -i $PEM_FILE $EC2_USER@$EC2_IP "mkdir -p ~/$PROJECT_DIR"

# 3. Transferir arquivos para a instância EC2
log_message "Transferindo arquivos..."
scp -i $PEM_FILE -r ./* $EC2_USER@$EC2_IP:~/$PROJECT_DIR/

# 4. Conectar à instância EC2 e executar comandos
log_message "Conectando à instância EC2 e executando comandos..."
# shellcheck disable=SC2087
ssh -i $PEM_FILE $EC2_USER@$EC2_IP << EOF
    # 5. Navegar até a pasta do projeto
    cd ~/$PROJECT_DIR

    # 6. Modificar o docker-compose.yml
    log_message "Atualizando docker-compose.yml..."
    sed -i "s|SPRING_MVC_CORS_ALLOWED-ORIGINS: http://localhost:8081|SPRING_MVC_CORS_ALLOWED-ORIGINS: http://$EC2_IP|g" docker-compose.yml
    sed -i 's|"8081:80"|"80:80"|g' docker-compose.yml

    # 7. Atualizar o nginx.conf
    log_message "Atualizando nginx.conf..."
    sed -i 's|proxy_pass http://urlshortner:8080/;|proxy_pass http://app:8080/;|g' urlfront/nginx.conf

    # 8. Construir o projeto backend
    log_message "Construindo o projeto backend..."
    docker build -t url-shortner-fullstack-app/urlshortener ./urlshortner

    # 9. Construir o projeto frontend
    log_message "Construindo o projeto frontend..."
    docker build -t url-shortner-fullstack-app/front ./urlfront

    # 10. Construir e iniciar os containers
    log_message "Construindo e iniciando os containers..."
    docker-compose up -d --build

    # 11. Verificar se os containers estão rodando
    log_message "Verificando status dos containers..."
    docker ps

    # 12. Verificar logs dos containers
    log_message "Verificando logs dos containers..."
    docker-compose logs

    # 13. Testar acesso ao frontend e backend
    log_message "Testando acesso..."
    curl http://localhost
    curl http://localhost/api/health

    log_message "Configuração e implantação concluídas."
    log_message "Acesse sua aplicação em: http://$EC2_IP"
EOF

log_message "Script de configuração e implantação finalizado."