from matplotlib import pyplot as plt


def plot_sample(data, title):
    counts, bins, patches = plt.hist(data, bins=50, edgecolor='black', alpha=0.7)

    for count, x in zip(counts, bins):
        plt.text(x, count, str(int(count)), fontsize=9, ha='center', va='bottom')

    plt.title(title)
    plt.xlabel("Згенеровані числа")
    plt.ylabel("Частота")
    plt.show()


def plot_intervals(intervals, frequencies, h, theoretic_density=None):
    plt.title(f'Графік к-сті влучень у {len(frequencies)} інтервалів')
    plt.hist(intervals[:-1], bins=intervals, weights=frequencies, edgecolor='black', alpha=0.7)
    # plt.bar(intervals[:-1], frequencies, width=h, edgecolor='black', alpha=0.7)
    plt.xticks(intervals, rotation=90)
    plt.xlabel('Інтервали')
    plt.ylabel('Частота')

    for i in range(len(frequencies)):
        plt.text(intervals[i] + h / 2, frequencies[i], str(frequencies[i]), ha='center', va='bottom')

    if theoretic_density:
        theoretic_density = [i for i in theoretic_density]
        mid_points = [(intervals[i] + intervals[i + 1]) / 2 for i in range(len(intervals) - 1)]
        plt.plot(mid_points, theoretic_density, color='red', marker='o', label='Очікувана к-сть влучень')
        plt.legend()

    plt.show()
