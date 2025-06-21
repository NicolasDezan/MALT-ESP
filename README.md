# MALT-ESP Android APP

Este repositório contém o projeto Android desenvolvido como parte do meu TCC. O projeto consiste em uma aplicação que utiliza da comunicação Bluetooth Low Energy (BLE) para ler e escrever dados em um ESP32-C3 que, por sua vez, é programado para atuar como um malteador laboratorial de grãos.

## Sobre o Projeto

O MALT-ESP Android APP é uma aplicação desenvolvida para controle e monitoramento de um malteador laboratorial de grãos, utilizando um microcontrolador ESP32-C3. O aplicativo se comunica com o ESP32-C3 via Bluetooth, permitindo que o usuário envie comandos e receba dados em tempo real sobre o processo de malteação.

Este projeto foi desenvolvido como parte do meu Trabalho de Conclusão de Curso (TCC) em Química Industrial e tem como objetivo principal atuar como uma ferramenta de auxílio para o planejamento e a execução de experimentos na área de cerveja/malteação.

### Funcionalidades

- **Comunicação Bluetooth (BLE):**
  - Envio e recebimento de parâmetros
  - Envio de comandos de start e stop
  - Recebimento da leitura de sensores e estado de atuadores

- **Interface Amigável:**
  - Utilização de Jetpack Compose
  - Material Design 3

- **Banco de dados:**
  - Permite o salvamento de receitas de parâmetros
  - Gerencia as preferências do usuário

### Como Executar

1. Baixe o arquivo .apk presente em releases e instale no seu celular. O aplicativo rodará sem funcionalidades caso não tenha o ESP32 com o Firmware do malteador - nesse caso, você pode só apreciar a beleza estética dele `:smiley:`.
Não se preocupe, o aplicativo é seguro (durante a instalação pode haver algum aviso do sistema do celular). 

2. Clone o repositório do código do microcontrolador e siga os passos de instalação do firmware no seu respectivo repositório:

   ```bash
   # Repositório ESP32C3-MaltingControl (Optativo)
   git clone https://github.com/NicolasDezan/ESP32C3-MaltingControl.git
   ```