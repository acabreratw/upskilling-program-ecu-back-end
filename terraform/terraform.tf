terraform {
  required_version = "0.12.26"
  backend "s3" {
    encrypt = true
    region = "us-east-2"
    bucket = "project-be-tf-state-bucket"
    key = "tf-state-bucket/remote.tfstate"

    dynamodb_table = "project-be-tf-state-lock-dynamo"
  }
}