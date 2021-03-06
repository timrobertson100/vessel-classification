# Copyright 2017 Google Inc. and Skytruth Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import setuptools
import glob
import os

data_files = [os.path.basename(x)
              for x in glob.glob("classification/data/*.csv")]

setuptools.setup(
    name='vessel_classification',
    version='1.0',
    author='Alex Wilson',
    author_email='alexwilson@google.com',
    package_data={
        'classification.data': data_files
    },
    packages=[
        'common',
        'classification',
        'classification.data',
        'classification.models',
        'classification.models.prod',
        'classification.models.dev',
    ],
    install_requires=[
        'NewlineJSON'
    ])
