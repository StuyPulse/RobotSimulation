# Graphics 3d

## TODO

* Add unidirectional light source (easy to calculate, especially in fragment shader)
* Instead of putting all the vertices in gpu memory, replace it with a vertex pool and triangles made up of indices
* Add color to triangles (or to each vertex)
* Consider changing the way that `Window` and `Renderer` execute their code. As of now, you cannot switch shaders between drawing because actually rendering all the meshes is not handled by the user. Window probably doesn't need to encapsulate the `glSwapBuffer` and `glClear` the way it does in update. These can just be called by the user. This would also remove the need for `RenderObject` as one can just pass in transform as well as a mesh. 
* Add floor, skybox, smoother camera movement?
* Add description of project
