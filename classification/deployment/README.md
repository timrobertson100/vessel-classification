# classification

This docker image is meant to contain an environment for classification
pipeline, allowing you to train, run inference and evaulation on a set of
training and test data.

## Usage

This image is meant to be used in two ways:

* In your development environment, for running the various scripts locally in
  an identical environment to the production one. In this case, you will want
to use the `dev` script we provide at the project root, which automatically
pulls the image from gcr and then runs a container with the image that has your
local project directory mounted at the workdir.

* In the production environment, the built image contains a compiled copy of
  the code, so that you can cloudml jobs and inference/evaluation scripts using
what was bundled in the image.

## Building

We provide a build script here that builds and publishes the image to gcr.io.
Just run `build` for this. You can optionally provide a tag name, such as
`build 0.1`, so that the image gets tagged appropriately. If not, the default
will be `latest`.

