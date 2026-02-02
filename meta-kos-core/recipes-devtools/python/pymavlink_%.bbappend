# 1. Remove the native dependency to stop BitBake from looking for a real recipe
DEPENDS:remove = "python3-future-native"

do_configure:prepend() {
    # 2. Create a 'mock' future module in the build's temporary site-packages.
    # This satisfies the 'import future' call during the build without network access.
    mkdir -p ${STAGING_LIBDIR_NATIVE}/python3.12/site-packages/future
    touch ${STAGING_LIBDIR_NATIVE}/python3.12/site-packages/future/__init__.py

    # 3. Fix the 'imp' module removal in Python 3.12
    # We still need this because 'imp' is physically gone from the Python 3.12 standard library.
    sed -i 's/import imp/import importlib as imp/g' ${S}/generator/mavgen.py || true
}