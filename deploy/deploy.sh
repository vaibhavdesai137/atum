#!/bin/sh

# Deploy to preprod
echo "\nDeploying to preprod using ansible..."
ansible-playbook deploy-playbook.yml --extra-vars "image_tag=$1" -l preprod -i inventory.txt -u root -k
rc=$?
if [[ $rc -ne 0 ]] ; then
  echo "\nPreProd deploy failed, exiting..."; 
  exit $rc
fi

# Run integration tests
echo "\nRunning integration tests..."
mvn verify
rc=$?
if [[ $rc -ne 0 ]] ; then
  echo "\nIntegration tests failed"; 
  exit $rc
fi

# Deploy to prod
echo "\nDeploying to prod using ansible..."
ansible-playbook deploy-playbook.yml --extra-vars "image_tag=$1" -l prod -i inventory.txt -u root -k
rc=$?
if [[ $rc -ne 0 ]] ; then
  echo "\nProd deploy failed, exiting..."; 
  exit $rc
fi
