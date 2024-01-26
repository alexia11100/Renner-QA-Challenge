# QA-Challenge-UI

## Informações de Ambiente
- Windows 10 64 bits
- JDK Amazon Corretto 17
- Intellij IDEA Community Edition 2023.3.2

## Como executar
- Baixe o Chrome Driver compatível com a versão do seu navegador
- Abra o diretório atual no Intellij
- Caso não tenha o JDK 17 instalado, clique na engrenagem que aparece no lado superior direito, vá em Project Structure e em SDK você consegue baixar e definir o JDK e aplicar as mudanças.
- No arquivo pom.xml dá um reload no maven para baixar as dependências
- Em src/test/java/tests.user abra o arquivo TestCase1
- Busque por uma linha comentada com `// Set the chromedriver path here` e defina o caminho para o executável do seu Chrome Driver
- Execute o teste
