import numpy as np


def get_dispersion(mean, sample):
    dispersion = 0
    for val in sample:
        dispersion += (val - mean) ** 2
    return dispersion / (len(sample) - 1)


def init_stats(distribution, sample, k=20):
    min_val = min(sample)
    max_val = max(sample)
    h = (max_val - min_val) / k
    mean = sum(sample) / len(sample)
    variance = get_dispersion(mean, sample)
    print(f"\n{distribution}:\n"
          f"min: {min_val}, max: {max_val}, довжина інтервалу: {h};\n"
          f"середнє μ: {mean}, дисперсія σ^2: {variance}")

    intervals = list([min_val + i * h for i in range(k + 1)])
    frequencies = list(np.histogram(sample, bins=intervals)[0])

    return mean, h, intervals, frequencies
