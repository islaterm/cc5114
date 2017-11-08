# CC5114 - Redes Neuronales y Programación Genética 2017, Primavera
**Estado actual del repositorio:**

Se implementó una interfaz `INeuron` que representa un neuron general y clases para representar neurones binarios (que 
aceptan exactamente dos valores de input) para representar los operadores lógicos binarios `AND`, `OR` y `NAND`.
Un neuron general puede recibir un número indefinido de inputs y puede ser entrenado.

Existen 2 implementaciones de `INeuron`: `Perceptron` y `SigmoidNeuron`.

La clase `BitSum` utiliza las clases definidas anteriormente para sumar dos bits.

La clase `PatternRecognizer` utiliza el algoritmo de aprendizaje del neuron para determinar si una serie de puntos
generados aleatoriamente están por sobre o bajo una recta.