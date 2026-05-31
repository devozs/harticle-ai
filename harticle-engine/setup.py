from setuptools import setup, find_namespace_packages

setup(
    name="harticle",
    version="0.0.1",
    description="Devoz Fake Sport Articles",
    packages=find_namespace_packages(),
    install_requires=[
        'requests',
    ],
    url="https://github.io/devozs/harticle",
    author="Devozs Ltd.",
    author_email="ozishemesh@gmail.com",
    project_urls={
        "Documentation": "https://github.io/devozs/harticle",
    },
    long_description=open('README.md').read(),
    long_description_content_type='text/markdown',
)
