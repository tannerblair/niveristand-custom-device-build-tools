def call(repo, branch){
  echo "Synching ${repo} to workspace."
  syncDir = repo.tokenize("/").last()
  
  bat "mkdir ${syncDir}"
  
  if(!branch){
    branch = 'master'
  }
  
  dir(syncDir){
    git url: repo, branch: branch
  }
  
  return syncDir
}
