FROM node:20 as build-stage
WORKDIR /app
# Copy package.json and package-lock.json
COPY front/package*.json ./
RUN npm install
# Install Angular CLI globally
RUN npm install -g @angular/cli@latest
# Copy source code
COPY front/ .
# Build the application
RUN ng build --configuration production

FROM nginx:alpine
# Copy nginx configuration
COPY front/nginx.conf /etc/nginx/nginx.conf
# Copy built application files
COPY --from=build-stage /app/dist/front /usr/share/nginx/html
EXPOSE 80
