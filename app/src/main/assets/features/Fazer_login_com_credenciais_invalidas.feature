Feature: Tentativa de cadastro com login e senha inexistentes
  Fazer fentativa de cadastro com login e senha inexistentes
  
  Scenario: fluxo completo para realizar tentativa falha com usuário e senha inexistentes
    Given Eu clico em "crm/cmz"
    Given Eu preencho o campo "crm/cmz"
    Given Eu clico em "senha"
	Given Eu preencho o campo "senha"
    Then Eu vejo a mensagem "Falha no login, usuário ou senha inválidos"