FROM ubuntu:16.04
# Install APT dependencies
RUN apt-get -y update && \
    apt-get -y install python python-setuptools python-dev build-essential git curl libcurl3

# Install python dependencies
RUN easy_install pip && \
    pip install --upgrade pip
RUN pip install https://storage.googleapis.com/tensorflow/linux/cpu/tensorflow-0.11.0rc0-cp27-none-linux_x86_64.whl && \
    pip install google-api-python-client pyyaml pytz newlinejson python-dateutil yattag

# Install google cloud
RUN curl -sSL https://sdk.cloud.google.com | bash && \
  /root/google-cloud-sdk/bin/gcloud config set --installation component_manager/disable_update_check true
ENV PATH $PATH:/root/google-cloud-sdk/bin

ADD ./ /app
WORKDIR /app
