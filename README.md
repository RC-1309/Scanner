# Scanner
Моя реализация util.Scanner на основе Reader, работающий быстрее оригинала.

Оригинал задания доступен по [ссылке](https://www.kgeorgiy.info/courses/prog-intro/homeworks.html#scanner).

Работа написанного класса показана с помощью его использования в других дз (и их модификаций по [этой](https://www.kgeorgiy.info/git/geo/prog-intro-2022/) ссылке) с того же сайта: Reverse и Wspp. 

## Reverse

- Разработайте класс ReverseOctAbc, читающий числа из стандартного ввода, и выводящий их на стандартный вывод в обратном порядке.
- На вход подаются десятичные и восьмиричные числа
- Восьмиричные числа имеют суффикс o
- Порядок строк в выходе должен быть обратным по сравнению с порядком строк во входе. Порядок чисел в каждой строке также должен быть обратным к порядку чисел во входе.
- Десятичные числа могут быть записаны буквами: нулю соответствует буква a, единице – b и так далее
- Вход содержит не более 10^6 чисел и строк.
- Примеры работы программы:
```sh
---
ввод
---
1 2
3
---
вывод
---
3
2 1
---
ввод
---
1 2
55o
2a
---
вывод
---
20 
45 
2 1 
---
```

## Wspp

- Разработайте класс Wspp, который будет подсчитывать статистику встречаемости слов во входном файле.
- Словом называется непрерывная последовательность букв, апострофов и тире (Unicode category Punctuation, Dash). Для подсчета статистики, слова приводятся к нижнему регистру.
- Выходной файл должен содержать все различные слова, встречающиеся во входном файле, в порядке их появления. Для каждого слова должна быть выведена одна строка, содержащая слово, число его вхождений во входной файл и номера вхождений этого слова среди всех слов во входном файле.
- Имена входного и выходного файла задаются в качестве аргументов командной строки. Кодировка файлов: UTF-8.
- Программа должна работать за линейное от размера входного файла время.
- Для реализации программы используйте Collections Framework.
- Сложный вариант. Реализуйте и примените класс IntList, компактно хранящий список целых чисел.
- Пример работы программы:

```sh
---
ввод
---
Monday's child is fair of face.
Tuesday's child is full of grace.
---
вывод
---
monday's 1 1
child 2 2 8
is 2 3 9
fair 1 4
of 2 5 11
face 1 6
tuesday's 1 7
full 1 10
grace 1 12
---
```

### Модификация
- Класс должен иметь имя WsppCountLastL
- В выходном файле слова должны быть упорядочены по возрастанию числа вхождений, а при равном числе вхождений – по порядку первого вхождения во входном файле.
- Вместо номеров вхождений во всем файле надо указывать только последнее вхождение в каждой строке.
- Пример

```sh
---
ввод
---
Monday's child is fair of face.
Tuesday's child is full of grace.
---
вывод
---
monday's 1 1
fair 1 4
face 1 6
tuesday's 1 1
full 1 4
grace 1 6
child 2 2 2
is 2 3 3
of 2 5 5
---
```
