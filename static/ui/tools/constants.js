import path from 'path';

const repoRoot = path.resolve(__dirname, '../');

const srcRoot = path.join(repoRoot, 'src/');
const distRoot = path.join(repoRoot, 'dist/');
const docsRoot = path.join(repoRoot, 'docs-built/');

export {
  repoRoot,
  srcRoot,
  distRoot,
  docsRoot
};
