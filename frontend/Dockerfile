# Stage 1: Build Angular app
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./

# Install dependencies
RUN npm install -g @angular/cli && npm install

# Copy source files
COPY . .

# ⚡ Build normal client (disable SSR)
RUN npm run build -- --configuration production




# Stage 2: Serve with Nginx
FROM nginx:latest
COPY --from=build /app/dist/bug-tracking/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
