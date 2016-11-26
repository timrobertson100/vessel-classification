FROM java:8

# Prepare dependencies
RUN apt-get update && \
  apt-get install -y apt-transport-https ca-certificates unzip curl libcurl3

# Install SBT
RUN echo "deb https://dl.bintray.com/sbt/debian /" > /etc/apt/sources.list.d/sbt.list && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
    apt-get update && \
    apt-get install -y sbt

# Install protoc
RUN wget https://github.com/google/protobuf/releases/download/v3.1.0/protoc-3.1.0-linux-x86_64.zip -O /tmp/protoc.zip && \
    unzip /tmp/protoc.zip -d /usr/lib/protoc && \
    rm /tmp/protoc.zip && \
    ln -s /usr/lib/protoc/bin/protoc /usr/bin/protoc

# Install google cloud
RUN curl -sSL https://sdk.cloud.google.com | bash && \
  /root/google-cloud-sdk/bin/gcloud config set --installation component_manager/disable_update_check true
ENV PATH $PATH:/root/google-cloud-sdk/bin

# Add and compile the project
ADD ./ /app
WORKDIR /app

