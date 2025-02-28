# MALT-ESP Android APP

Este repositório contém o projeto Android que está sendo desenvolvido como parte do meu TCC. O projeto consiste em uma aplicação Android que utiliza da comunicação Bluetooth para ler e escrever dados em um ESP32-C3 que, por sua vez, é programado para atuar como um malteador laboratorial de grãos.

## Sobre o Projeto

O MALT-ESP Android APP é uma aplicação desenvolvida para controle e monitoramento de um malteador laboratorial de grãos, utilizando um microcontrolador ESP32-C3. O aplicativo se comunica com o ESP32-C3 via Bluetooth, permitindo que o usuário envie comandos e receba dados em tempo real sobre o processo de malteação.

Este projeto está sendo desenvolvido como parte do meu Trabalho de Conclusão de Curso (TCC) em Química Industrial e tem como objetivo principal atuar como uma ferramenta de auxílio para o planejamento e a execução de experimentos.

### Funcionalidades (ainda em desenvolvimento)

- **Comunicação Bluetooth (BLE):**
  - Envio e recebimento de parâmetros
  - Envio de comandos de start, pause e stop
  - Recebimento da leitura de sensores

- **Interface Amigável:**
  - Utilização de Jetpack Compose
  - Material Design

- **Banco de dados:**
  - Permite o salvamento de receitas de parâmetros
  - Gerencia as preferências do usuário

### Como Executar

Para executar esse aplicativo é necessário ter o Android Studio instalado e um dispositivo Android que suporte Bluetooth. Também é interessante que se tenha acesso a algum microcontrolador configurado com o código do repositório ESP32C3-MaltingControl. 

1. Clone este repositório e, se puder, o repositório do código do microcontrolador:
   
   ```bash
   # Repositório MALT-ESP
   git clone https://github.com/NicolasDezan/MALT-ESP.git
   ```
   ```bash
   # Repositório ESP32C3-MaltingControl (Optativo)
   git clone https://github.com/NicolasDezan/ESP32C3-MaltingControl.git
   ```
   
2. Abra o projeto no Android Studio como um projeto já existente
   
3. Ative a depuração por wifi ou usb e conecte o celular no computador
   
5. Rode a aplicação
