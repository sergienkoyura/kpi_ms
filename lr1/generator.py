import random

import numpy as np


def generate_exponential(lambda_val, n):
    generated_list = [random.Random().random() for _ in range(n)]
    return -np.log(generated_list) / lambda_val


def generate_normal(sigma, a, n):
    return [sigma * (sum(random.Random().random() for _ in range(12)) - 6) + a for _ in range(n)]


def generate_uniform(a, c, n):
    generated_list = []
    prev = 1
    for i in range(n):
        prev = (a * prev) % c
        generated_list.append(prev / c)
    return generated_list
