#!/bin/bash
set -e
mkdir -p /var/app/current/uploads
chown webapp:webapp /var/app/current/uploads || true
chmod 775 /var/app/current/uploads
