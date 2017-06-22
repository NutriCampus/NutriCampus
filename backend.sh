#!/bin/sh
# 1- esse bash altera o index.html do report para colocar o commit id e link do commit
# 2- faz commit com uma deploy key para o projeto de reports no github

echo "INICIOU ARQUIVO BACKEND.SH"
username='NutriCampus'
mainprojname='NutriCampus'
reportprojname='NutriCampusUnitTestReport'
githubmainproject='https://github.com/'$username'/'$mainprojname
githubreportproject='https://github.com/'$username'/'$reportprojname
echo "github projeto: "$githubmainproject
echo "github projeto report: "$githubreportproject

#pathprojeto='/home/matt/IDEs_workspaces/AndroidStudioProjects/'$mainprojname'/' #comentar essa linha quando rodar no travis
pathprojeto='/home/travis/build/'$username'/'$mainprojname'/' #comentar essa linha quando rodar localmente
echo "path home do projeto no travis: "$pathprojeto

pathprojetoreport=$pathprojeto$reportprojname'/'
echo "path home do projeto report no travis: "$pathprojetoreport
pathreport=$pathprojeto'app/build/reports/tests/testDebugUnitTest/'
pathindexfile=$pathreport'index.html'
pathindexfiletemp=$pathreport'temp'

#pego os dados do commit principal
commitidLONG=`cd $pathprojeto && git rev-parse HEAD`
echo "commit id LONG: "$commitidLONG
commitidSHORT=`cd $pathprojeto && git rev-parse --short HEAD`
echo "commit id SHORT: "$commitidSHORT
#ismaster=`cd $pathprojeto && git rev-parse --abbrev-ref HEAD`
#ismaster=`cd $pathprojeto && git branch | grep \* | cut -d ' ' -f2`
ismaster=`cd $pathprojeto && git branch`
echo "current branch: "$ismaster

#só faz o deployment do report se for o branch master que foi atualizado
if [ "$ismaster" = "* master" ];
then
	echo "is Master, then do scheme"
	#regex
	tofind1='<h1>Test Summary</h1>'
	tofind2='<div id="footer">'

	replacewith1='<h1>Test Summary</h1 <h4>main commit: <a href="'$githubmainproject'/commit/'$commitidLONG'" target="_blank">'$commitidSHORT'<a></h4>'
	replacewith2='<div id="footer"> Main project  @ <a href="'$githubmainproject'" target="_blank">'$mainprojname'</a><br /> Reports hosted @ <a href="'$githubreportproject'" target="_blank">'$reportprojname'</a>'
	#escrevo o conteudo no novo arquivo
	echo "-----ALTERANDO ARQUIVO index.html-----"
	cat $pathindexfile | while read line
	do
		if [ "$line" = "$tofind1" ];
		then
			echo "ADICIONANDO LINK DO MAIN COMMIT AO index.html"
			echo $replacewith1 >> $pathindexfiletemp
		elif [ "$line" = "$tofind2" ];
		then
		echo "ADICIONANDO INFORMAÇÕES EXTRAS AO index.html"
			echo $replacewith2 >> $pathindexfiletemp
		else
			echo $line >> $pathindexfiletemp
		fi	
	done
	echo "-----ALTERAÇÕES CONCLUÍDAS NO ARQUIVO index.html-----"

	#passo para o arquivo original
	indexhtml=`cat $pathindexfiletemp`
	echo $indexhtml > $pathindexfile
	echo "CONTEÚDO DO ARQUIVO TEMPORÁRIO PASSADO PARA O index.html"

	#excluo arquivo tmeporario
	rm $pathindexfiletemp
	echo "ARQUIVO TEMPORÁRIO DELETADO"


	echo "-------------------------------------------------------------------"
	echo $indexhtml
	echo "-------------------------------------------------------------------"
	#fazer clone na raiz
	cd $pathprojeto
	
	#apago pasta se já existir
	`rm -rf $pathprojetoreport`
	echo "PASTA "$pathprojetoreport" APAGADA (SE EXISTIR)"

	#clono repositorio novamente e deleto tudo de dentro exceto pasta .git e README.md
	`git clone $githubreportproject'.git' && cd $pathprojetoreport && find . \! -name '.git'  \! -name 'README.md' -delete`
	echo "PROJETO "$reportprojname" (GITHUB) CLONADO E CONTEÚDO APAGADO, EXCETO PASTA .git|README.md"

	#copiar arquivos da pasta dos relatórios para pasta clonada
	cp -a $pathreport. $pathprojetoreport
	echo "COPIADO CONTEÚDO DE "$pathreport" PARA "$pathprojetoreport

	#acessar pasta do repositório report e commitar mudanças
	cd $pathprojetoreport
	git add -A && git commit -m "From commit: "$githubmainproject"/commit/"$commitidLONG && git push -u origin master
fi
echo "FINALIZOU ARQUIVO BACKEND.SH"