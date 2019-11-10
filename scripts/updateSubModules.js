git submodule update --init huhiSync
git submodule update --init huhi-crypto
git submodule update huhiSync
git submodule update huhi-crypto
cd huhiSync
npm install
npm run build
cd ..
cd huhi-crypto
npm install
npm run build
