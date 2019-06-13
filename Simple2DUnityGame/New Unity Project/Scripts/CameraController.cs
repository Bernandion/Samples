using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour {


	// Use this for initialization
	void Start () {

        //Screen.SetResolution(640, 480, false);

    }
	
	// Update is called once per frame
	void Update () {
        //cam.pixelRect = this.GetComponent<Rect>();
        //cam.aspect = GetComponent<Rect>().width / Screen.height;
	}

    void OnCollisionEnter2D(Collision2D collision) {
        if (collision.gameObject.tag == "shot" || collision.gameObject.tag == "eshot") {
            Destroy(collision.gameObject);
        }
        if (collision.gameObject.tag == "enemy") {
            Physics2D.IgnoreCollision(collision.gameObject.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
    }

    
}
