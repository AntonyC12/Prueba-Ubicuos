FROM nginx:alpine

# Eliminar config default
RUN rm -rf /usr/share/nginx/html/*

# Copiar frontend
COPY . /usr/share/nginx/html

# Exponer puerto
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
