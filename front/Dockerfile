FROM node:12

ENV PORT 3000

# Copying source files
COPY . /usr/src/app

# Building app
RUN npm install
RUN npm run build
EXPOSE 3000

# Running the app
CMD "npm" "run" "dev"