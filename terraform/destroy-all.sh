#!/bin/bash
terraform init
terraform workspace select int
terraform destroy -auto-approve

terraform workspace select stg
terraform destroy -auto-approve