#!/bin/bash
terraform init
terraform workspace select int-test
terraform destroy -auto-approve

terraform workspace select stg-test
terraform destroy -auto-approve