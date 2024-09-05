# Stage 1: Build the Angular application
FROM node:18 AS build-stage
WORKDIR /app
# Copy package.json and package-lock.json
COPY package*.json ./
RUN npm cache clean --force && npm install
# Install Angular CLI globally
RUN npm install -g @angular/cli@16.2.14
# Copy source code
COPY . .
# Build the application
RUN ng build --configuration production

# Stage 2: Serve the application using Nginx
FROM nginx:alpine
# Copy built application files
COPY --from=build-stage /app/dist/urlfront /usr/share/nginx/html
# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf
# Expose port 80
EXPOSE 80
# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
