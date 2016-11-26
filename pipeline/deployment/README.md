# pipeline

This docker image is meant to contain an environment for the scala pipeline,
allowing you to compile, test and deploy pipeline jobs to google cloud
dataflow.

## Usage

This image is meant to be used in two ways:

* In your development environment, for running sbt locally in an identical
  environment to the production one. In this case, you will want to use the
`dev` script we provide at the project root, which automatically pulls the
image from gcr and then runs a container with the image that has your local
project directory mounted at the workdir.

* In the production environment, the built image contains a compiled copy of
  the pipeline code, so that you can launch dataflow jobs using what was
bundled in the image.

## Building

We provide a build script here that builds and publishes the image to gcr.io.
Just run `build` for this. You can optionally provide a tag name, such as
`build 0.1`, so that the image gets tagged appropriately. If not, the default
will be `latest`.

