from setuptools import setup, find_namespace_packages

setup(
    name="harticle",
    version="0.0.1",
    description="Devoz Fake Sport Articles",
    packages=find_namespace_packages(),
    install_requires=[
        'requests',
    ],
    extras_require={
        # Base training agent (CPU/stub): the poll loop + transformers stack.
        'training': [
            'transformers>=4.40',
            'datasets>=2.18',
            'boto3>=1.34',
        ],
        # NVIDIA GPU box: install a CUDA-enabled torch build for your platform.
        'cuda': [
            'torch>=2.1',
        ],
        # Intel Gaudi VM: normally provided by Habana's base image; pinned here
        # for reference. habana_frameworks is NOT pip-installable standalone.
        'gaudi': [
            'optimum-habana>=1.11',
        ],
    },
    url="https://github.io/devozs/harticle",
    author="Devozs Ltd.",
    author_email="ozishemesh@gmail.com",
    project_urls={
        "Documentation": "https://github.io/devozs/harticle",
    },
    long_description=open('README.md').read(),
    long_description_content_type='text/markdown',
)
