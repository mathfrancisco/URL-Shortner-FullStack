FROM node:16 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install -g @angular/cli@latest

COPY . .
RUN npm run build --configuration production

# Stage 2: Run with Nginx
FROM nginx:alpine

# Copy nginx.conf
COPY nginx.conf /etc/nginx/nginx.conf

# Copy built Angular app
COPY --from=build /app/dist/front /usr/share/nginx/html

# Remove default nginx configuration
RUN rm /etc/nginx/conf.d/default.conf

# Start nginx
CMD ["nginx", "-g", "daemon off;"]

