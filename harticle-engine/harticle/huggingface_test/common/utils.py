import os
from os.path import exists
import torch
from GPUtil import showUtilization as gpu_usage
from numba import cuda


def clean_model(root):
    directory = "./{}".format(root)
    if exists(directory):
        for root, dirs, files in os.walk(directory, topdown=False):
            for name in files:
                os.remove(os.path.join(root, name))
            for name in dirs:
                os.rmdir(os.path.join(root, name))
    if exists(directory):
        os.rmdir(directory)


def free_gpu_cache():
    print("Initial GPU Usage")
    gpu_usage()

    torch.cuda.empty_cache()

    # cuda.select_device(0)
    # cuda.close()
    # cuda.select_device(0)

    print("GPU Usage after emptying the cache")
    gpu_usage()


def tr_loss(device):
    loss = torch.tensor(0.0).to(device)
    print(tr_loss)
    return loss
