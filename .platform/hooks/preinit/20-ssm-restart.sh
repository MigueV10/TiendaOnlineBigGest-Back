#!/bin/bash
set -e
# 1) Asegura SSM activo
systemctl enable amazon-ssm-agent || true
systemctl restart amazon-ssm-agent || true

# 2) Vuelca estado y últimas 200 líneas al syslog (termina en /var/log/messages)
echo "=== SSM STATUS ===" | logger -t eb-ssm
systemctl is-active amazon-ssm-agent | logger -t eb-ssm
systemctl status amazon-ssm-agent --no-pager | sed 's/^/[status] /' | logger -t eb-ssm
journalctl -u amazon-ssm-agent --no-pager -n 200 | sed 's/^/[journal] /' | logger -t eb-ssm
