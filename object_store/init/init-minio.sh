#!/bin/sh
set -e

echo "Waiting for MinIO to be ready..."

# Keep trying to set the alias until MinIO is up
until mc alias set local http://minio:9000 "$MINIO_ROOT_USER" "$MINIO_ROOT_PASSWORD"
do
  echo "MinIO not ready yet, sleeping 2s..."
  sleep 2
done

echo "MinIO is ready"

# Bucket name
BUCKET_NAME=pokemon-images

# Create bucket if it does not exist
if ! mc ls local/$BUCKET_NAME >/dev/null 2>&1; then
  echo "Creating bucket: $BUCKET_NAME"
  mc mb local/$BUCKET_NAME
else
  echo "Bucket already exists: $BUCKET_NAME"
fi

# Copy all seed images
echo "Uploading seed images..."
mc cp --recursive --quiet /seed/* local/$BUCKET_NAME

# Set public read access
echo "Setting public read access..."
mc anonymous set download local/$BUCKET_NAME

echo "MinIO initialization complete"