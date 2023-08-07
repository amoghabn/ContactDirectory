#!/bin/bash

# Wait for MySQL service to start (timeout after 60 seconds)
timeout 60 bash -c 'while ! mysqladmin ping -h localhost --silent; do sleep 1; done'

# Check if the MySQL service is up and running
if [ $? -eq 0 ]; then
    # Create the user "cdapp" with the desired password
    mysql -e "CREATE USER 'cdapp'@'localhost' IDENTIFIED BY 'cdapp';"

    # Create the database "personalcontact"
    mysql -e "CREATE DATABASE personalcontact;"

    # Grant full permissions to the user "cdapp" for the "personalcontact" database
    mysql -e "GRANT ALL PRIVILEGES ON personalcontact.* TO 'cdapp'@'localhost';"

    # Flush privileges to apply the changes
    mysql -e "FLUSH PRIVILEGES;"

    echo "MySQL setup completed successfully."
else
    echo "Timeout: MySQL service did not start within the specified time."
fi

