#!/bin/sh
# 1- esse bash altera o index.html do report para colocar o commit id e link do commit
# 2- faz commit com uma personal key para o projeto de reports no github
echo "##################################################################"
echo "##################################################################"
echo "##################################################################"
echo "##################                        ########################"
echo "##################       BACKEND.SH       ########################"
echo "##################                        ########################"
echo "##################################################################"
echo "##################################################################"
echo "##################################################################"

#pego hashpassword DO PARAMETRO DE ENTRADA para fazer commit (utilizando Environment Variables do travis)
password=$1
branch=$2
horalocal=$3
username='NutriCampus'
mainprojname='NutriCampus'
reportprojname='NutriCampusUnitTestReport'
usernameofpersonalkey='tronipm'
githubmainproject='https://github.com/'$username'/'$mainprojname
githubreportproject='https://github.com/'$username'/'$reportprojname
echo ">>>>>>> github projeto: "$githubmainproject
echo ">>>>>>> github projeto report: "$githubreportproject
echo "##################################################################"
echo "##################################################################"
#pathprojeto='/home/matt/IDEs_workspaces/AndroidStudioProjects/'$mainprojname'/' #comentar essa linha quando rodar no travis
pathprojeto='/home/travis/build/'$username'/'$mainprojname'/' #comentar essa linha quando rodar localmente
echo ">>>>>>> path home do projeto no travis: "$pathprojeto

pathprojetoreport=$pathprojeto$reportprojname'/'
echo ">>>>>>> path home do projeto report no travis: "$pathprojetoreport
pathreport=$pathprojeto'app/build/reports/tests/testDebugUnitTest/'
pathindexfile=$pathreport'index.html'
pathindexfiletemp=$pathreport'temp'
pathlint=$pathprojeto'app/build/reports/'
lintindex=$pathlint'lint-results.html'
lintresult=$pathlint'lint-results.xml'
pathinstrumented=$pathprojeto'app/build/reports/androidTests/connected/'
instrumentedindex=$pathinstrumented'index.html'
instrumentedindextemp=$pathinstrumented'temp'
apkpath=$pathprojeto'app/build/outputs/apk/'
apkfile=$apkpath'app-debug.apk'


#pego os dados do commit principal
commitidLONG=`cd $pathprojeto && git rev-parse HEAD`
echo ">>>>>>> commit id LONG: "$commitidLONG
commitidSHORT=`cd $pathprojeto && git rev-parse --short HEAD`
echo ">>>>>>> commit id SHORT: "$commitidSHORT
echo ">>>>>>> current branch (BRANCH ENVIRONMENT VARIABLE): "$branch

echo ">>>>>>> current local datetime: "$horalocal
echo "##################################################################"
echo "##################################################################"

if [ "$branch" = "master" ];
then

#regex
tofind1='<h1>Test Summary</h1>'
tofind2='<div id="footer">'

replacewith1='<h1>Test Summary</h1 <h4>main commit: <a href="'$githubmainproject'/commit/'$commitidLONG'" target="_blank">'$commitidSHORT'<a></h4>'
replacewith2='<div id="footer"> Main project  @ <a href="'$githubmainproject'" target="_blank">'$mainprojname'</a><br /> Reports hosted @ <a href="'$githubreportproject'" target="_blank">'$reportprojname'</a><br /> Local datetime @ '$horalocal
#escrevo o conteudo no novo arquivo
echo "-----ALTERANDO ARQUIVO index.html-----"
cat $pathindexfile | while read line
do
	if [ "$line" = "$tofind1" ];
	then
		echo ">>>>>>> ADICIONANDO LINK DO MAIN COMMIT AO index.html"
		echo $replacewith1 >> $pathindexfiletemp
	elif [ "$line" = "$tofind2" ];
	then
	echo ">>>>>>> ADICIONANDO INFORMAÇÕES EXTRAS AO index.html"
		echo $replacewith2 >> $pathindexfiletemp
	else
		echo $line >> $pathindexfiletemp
	fi	
done
echo ">>>>>>> -----ALTERAÇÕES CONCLUÍDAS NO ARQUIVO index.html-----"

#passo para o arquivo original
indexhtml=`cat $pathindexfiletemp`
echo $indexhtml > $pathindexfile
echo ">>>>>>> CONTEÚDO DO ARQUIVO TEMPORÁRIO PASSADO PARA O index.html"

#excluo arquivo tmeporario
rm $pathindexfiletemp
echo ">>>>>>> ARQUIVO TEMPORÁRIO DELETADO"


echo "##################################################################"
echo "##################################################################"
echo "-----ALTERANDO ARQUIVO INSTRUMENTED index.html-----"
cat $instrumentedindex | while read line
do
	if [ "$line" = "$tofind1" ];
	then
		echo ">>>>>>> ADICIONANDO LINK DO MAIN COMMIT AO index.html"
		echo $replacewith1 >> $instrumentedindextemp
	elif [ "$line" = "$tofind2" ];
	then
	echo ">>>>>>> ADICIONANDO INFORMAÇÕES EXTRAS AO index.html"
		echo $replacewith2 >> $instrumentedindextemp
	else
		echo $line >> $instrumentedindextemp
	fi	
done
echo ">>>>>>> -----ALTERAÇÕES CONCLUÍDAS NO ARQUIVO index.html-----"

#passo para o arquivo original
indexhtml=`cat $instrumentedindextemp`
echo $indexhtml > $instrumentedindex
echo ">>>>>>> CONTEÚDO DO ARQUIVO TEMPORÁRIO PASSADO PARA O index.html"

#excluo arquivo tmeporario
rm $instrumentedindextemp
echo ">>>>>>> ARQUIVO TEMPORÁRIO DELETADO"

echo "##################################################################"
echo "##################################################################"
#echo $indexhtml
#echo "-------------------------------------------------------------------"
#fazer clone na raiz
cd $pathprojeto

#apago pasta se já existir
rm -rf $pathprojetoreport && echo "PASTA "$pathprojetoreport" APAGADA"

#clono repositorio novamente e deleto tudo de dentro exceto pasta .git e README.md

git clone $githubreportproject'.git' && echo "PROJETO "$reportprojname" (GITHUB) CLONADO"
cd $pathprojetoreport

echo "##################################################################"
echo "##################################################################"
echo ">>>>>>> IMPRIMINDO NOMES DE ARQUIVOS DO COMMIT ATUAL NO "$reportprojname
ls -a
echo ">>>>>>> ----------------------------------------------------------"
#find . \! -name '.git' \! -name 'README.md' -delete && 	echo "CONTEÚDO APAGADO, EXCETO PASTA .git|README.md"

commitauxidlong=`cd $pathprojetoreport && git rev-parse HEAD`
echo ">>>>>>> "$reportprojname" commit id LONG: "$commitauxidlong
commitauxidshort=`cd $pathprojetoreport && git rev-parse --short HEAD`
echo ">>>>>>> "$reportprojname" commit id SHORT: "$commitauxidshort

#tirar erro de branch com mudanças locais
echo ">>>>>>> SETANDO CONFIGURAÇÕES DO GIT username E email"
git config user.email "builds@travis-ci.org"
git config user.name "Travis CI"
git config push.default simple
#git pull
git checkout master
#git checkout -b iss53
echo ">>>>>>> ----------------------------------------------------------"
mkdir 'lint'  && echo "PASTA 'lint' CRIADA"
#mkdir 'apk'  && echo "PASTA 'apk' CRIADA"
#copiar arquivos da pasta dos relatórios para pasta clonada
cp -a $pathreport. $pathprojetoreport"unit/" && echo ">>>>>>> COPIADO CONTEÚDO DE "$pathreport" PARA "$pathprojetoreport
cp -a $lintindex $pathprojetoreport"lint/index.html" && echo ">>>>>>> COPIADO "$lintindex" PARA "$pathprojetoreport"lint/index.html"
cp -a $pathinstrumented. $pathprojetoreport"instrumented/" && echo ">>>>>>> COPIADO "$pathinstrumented" PARA "$pathprojetoreport"instrumented/"

txtatualizarversaoindex='Updated: '$horalocal' | From commit: '$commitidSHORT
echo $txtatualizarversaoindex > 'versao.txt'


echo ">>>>>>> ----------------------------------------------------------"
echo ">>>>>>> IMPRIMINDO NOMES DE ARQUIVOS APÓS CONTEÚDO COPIADO"
ls -a
echo ">>>>>>> ----------------------------------------------------------"
echo ">>>>>>> IMPRIMINDO DIFERENÇAS: "
git commit
echo ">>>>>>> ----------------------------------------------------------"

echo "##################################################################"
echo "##################################################################"
echo ">>>>>>> COMEÇANDO ENVIO DE DADOS PARA REPOSITÓRIO DE REPORT"

git add -A && echo "GIT ADD SUCCESSFULLY"
git commit -m "From commit: "$githubmainproject"/commit/"$commitidLONG && echo "GIT COMMIT MASTER SUCCESSFULLY"
git push https://$usernameofpersonalkey:$password@github.com/$username/$reportprojname.git HEAD:master --force && echo "GIT PUSH MASTER SUCCESSFULLY"

echo "##################################################################"
echo "##################################################################"

cd $pathprojeto
rm -rf $pathprojetoreport && echo "PASTA "$pathprojetoreport" APAGADA"
git clone $githubreportproject'.git' && echo "PROJETO "$reportprojname" (GITHUB) CLONADO"
cd $pathprojetoreport

echo ">>>>>>> SETANDO CONFIGURAÇÕES DO GIT username E email"
git config user.email "builds@travis-ci.org"
git config user.name "Travis CI"
git config push.default simple

git checkout apk && echo ">>>>>>> BRANCH APK SELECIONADO"
echo ">>>>>>> ----------------------------------------------------------"
echo ">>>>>>> IMPRIMINDO NOMES DE ARQUIVOS APÓS SELEÇÃO DE BRANCH"
ls -a
echo ">>>>>>> ----------------------------------------------------------"
#rm -f app-debug.apk && echo "app-debug.apk APAGADO"
rm -rf * && echo "app-debug.apk APAGADO"
cp -a $apkfile $pathprojetoreport"apk/"$mainprojname"_"$commitidSHORT".apk" && echo ">>>>>>> COPIADO "$apkfile" PARA "$pathprojetoreport"apk/"$mainprojname".apk"
echo ">>>>>>> ----------------------------------------------------------"
echo ">>>>>>> IMPRIMINDO NOMES DE ARQUIVOS APÓS APK COPIADO"
ls -a
echo ">>>>>>> ----------------------------------------------------------"
git commit -m "From commit: "$githubmainproject"/commit/"$commitidLONG && echo "GIT COMMIT APK SUCCESSFULLY"
git push https://$usernameofpersonalkey:$password@github.com/$username/$reportprojname.git HEAD:apk --force && echo "GIT PUSH APK SUCCESSFULLY"

fi
echo "FINALIZOU ARQUIVO BACKEND.SH"
