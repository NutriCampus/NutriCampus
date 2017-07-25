Feature: Tentativa de cadastro com login e senha com sucesso
  Fazer tentativa de cadastro com dados válidos
  
  Scenario: fluxo completo para realizar tentativa de cadastro com dados válidos e completos
    Given Eu clico em campo de texto do "nome"
    Given Eu preencho o campo de texto "nome"
    Given Eu clico em campo de texto do "cpf"
    Given Eu preencho o campo de texto "cpf"
	Given Eu clico em campo de texto do "crm"
    Given Eu preencho o campo de texto "crm"
	Given Eu clico em campo de texto do "email"
    Given Eu preencho o campo de texto "email"
	Given Eu clico em campo de texto do "senha"
    Given Eu preencho o campo de texto "senha"
	Given Eu clico no botão "salvar"
    Then Eu vejo a mensagem "Usuário cadastrado"