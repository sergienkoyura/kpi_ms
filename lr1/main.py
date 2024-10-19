from scipy.stats import chi2, expon, norm, uniform

from generator import generate_exponential, generate_normal, generate_uniform
from statistics_data import init_stats
from statistics_plot import plot_intervals, plot_sample


def merge_intervals(intervals, frequencies):
    i = 0
    while i < len(frequencies):
        if frequencies[i] < 5:
            if i < len(frequencies) - 1:
                frequencies[i] += frequencies[i + 1]
                del frequencies[i + 1]
                del intervals[i + 1]
            elif i > 0:
                # last item
                frequencies[i - 1] += frequencies[i]
                del frequencies[i]
                del intervals[i]
        else:
            i += 1
    return intervals, frequencies


def chi_squared_test(frequencies, theoretic_frequencies, count_constants):
    alpha = 0.05  # рівень значущості
    df = len(frequencies) - count_constants - 1  # к-сть інтервалів - к-сть параметрів закону розподілу - 1
    chi2_test = 0
    for i in range(len(frequencies)):
        chi2_test += (frequencies[i] - theoretic_frequencies[i]) ** 2 / theoretic_frequencies[i]

    chi2_kr = chi2.ppf(1 - alpha, df)
    if chi2_test < chi2_kr:
        print(f"Закон розподілу відповідає спостережуваним значенням випадкової величини, {chi2_test} < {chi2_kr}")
    else:
        print(f"Закон розподілу не збігаєтсья зі спостережуваними значеннями випадкової величини, {chi2_test} > {chi2_kr}")


if __name__ == '__main__':
    n = 10000
    lambda_values = [0.01, 0.3, 6]
    sigma_values = [3, 20, 11]
    a_values = [50, 10, 6]
    a_unif_values = [5 ** 13, 75, 10]
    c_unif_values = [2 ** 31, 113, 3 ** 18]
    for step in range(3):
        distribution = 'Експоненціальний розподіл'
        sample = generate_exponential(lambda_values[step], n)
        mean, h, intervals, frequencies = init_stats(distribution, sample)
        # plot_sample(sample, distribution)
        # plot_intervals(intervals, frequencies, h)

        intervals, frequencies = merge_intervals(intervals, frequencies)
        theoretic_frequencies = []
        for i in range(len(intervals) - 1):
            theoretic_frequencies.append(
                (expon.cdf(intervals[i + 1], scale=mean) -
                 expon.cdf(intervals[i], scale=mean)) * n
            )

        plot_intervals(intervals, frequencies, h, theoretic_frequencies)
        chi_squared_test(frequencies, theoretic_frequencies, 1)

        distribution = 'Нормальний розподіл'
        sample = generate_normal(sigma_values[step], a_values[step], n)
        mean, h, intervals, frequencies = init_stats(distribution, sample)
        # plot_sample(sample, distribution)
        # plot_intervals(intervals, frequencies, h)

        intervals, frequencies = merge_intervals(intervals, frequencies)
        theoretic_frequencies = []
        for i in range(len(intervals) - 1):
            theoretic_frequencies.append(
                (norm.cdf(intervals[i + 1], loc=a_values[step], scale=sigma_values[step]) -
                 norm.cdf(intervals[i], loc=a_values[step], scale=sigma_values[step])) * n
            )

        plot_intervals(intervals, frequencies, h, theoretic_frequencies)
        chi_squared_test(frequencies, theoretic_frequencies, 2)

        distribution = 'Рівномірний розподіл'
        sample = generate_uniform(a_unif_values[step], c_unif_values[step], n)
        mean, h, intervals, frequencies = init_stats(distribution, sample)
        # plot_sample(sample, distribution)
        # plot_intervals(intervals, frequencies, h)

        intervals, frequencies = merge_intervals(intervals, frequencies)
        theoretic_frequencies = [n / len(intervals[:-1]) for _ in range(len(intervals) - 1)]

        # theoretic_frequencies = []
        # for i in range(len(intervals) - 1):
        #     theoretic_frequencies.append(
        #         (uniform.cdf(intervals[i + 1], loc=0, scale=1) -
        #         uniform.cdf(intervals[i], loc=0, scale=1)) * n
        #     )

        plot_intervals(intervals, frequencies, h, theoretic_frequencies)
        chi_squared_test(frequencies, theoretic_frequencies, 2)
