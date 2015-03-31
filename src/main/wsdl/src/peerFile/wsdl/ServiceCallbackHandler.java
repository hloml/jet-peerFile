
/**
 * ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package peerFile.wsdl;

    /**
     *  ServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for get_home_folder method
            * override this method for handling normal response from get_home_folder operation
            */
           public void receiveResultget_home_folder(
                    peerFile.wsdl.ServiceStub.Get_home_folderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_home_folder operation
           */
            public void receiveErrorget_home_folder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for browse method
            * override this method for handling normal response from browse operation
            */
           public void receiveResultbrowse(
                    peerFile.wsdl.ServiceStub.BrowseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from browse operation
           */
            public void receiveErrorbrowse(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create_entity method
            * override this method for handling normal response from create_entity operation
            */
           public void receiveResultcreate_entity(
                    peerFile.wsdl.ServiceStub.Create_entityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create_entity operation
           */
            public void receiveErrorcreate_entity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_entity_attributes method
            * override this method for handling normal response from get_entity_attributes operation
            */
           public void receiveResultget_entity_attributes(
                    peerFile.wsdl.ServiceStub.Get_entity_attributesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_entity_attributes operation
           */
            public void receiveErrorget_entity_attributes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for back_to_historic_version method
            * override this method for handling normal response from back_to_historic_version operation
            */
           public void receiveResultback_to_historic_version(
                    peerFile.wsdl.ServiceStub.Back_to_historic_versionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from back_to_historic_version operation
           */
            public void receiveErrorback_to_historic_version(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_version_list method
            * override this method for handling normal response from get_version_list operation
            */
           public void receiveResultget_version_list(
                    peerFile.wsdl.ServiceStub.Get_version_listResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_version_list operation
           */
            public void receiveErrorget_version_list(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_full_path_from_root method
            * override this method for handling normal response from get_full_path_from_root operation
            */
           public void receiveResultget_full_path_from_root(
                    peerFile.wsdl.ServiceStub.Get_full_path_from_rootResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_full_path_from_root operation
           */
            public void receiveErrorget_full_path_from_root(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create_folder method
            * override this method for handling normal response from create_folder operation
            */
           public void receiveResultcreate_folder(
                    peerFile.wsdl.ServiceStub.Create_folderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create_folder operation
           */
            public void receiveErrorcreate_folder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exists_file method
            * override this method for handling normal response from exists_file operation
            */
           public void receiveResultexists_file(
                    peerFile.wsdl.ServiceStub.Exists_fileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exists_file operation
           */
            public void receiveErrorexists_file(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create_entity_get_attributes method
            * override this method for handling normal response from create_entity_get_attributes operation
            */
           public void receiveResultcreate_entity_get_attributes(
                    peerFile.wsdl.ServiceStub.Create_entity_get_attributesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create_entity_get_attributes operation
           */
            public void receiveErrorcreate_entity_get_attributes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_lock_info method
            * override this method for handling normal response from get_lock_info operation
            */
           public void receiveResultget_lock_info(
                    peerFile.wsdl.ServiceStub.Get_lock_infoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_lock_info operation
           */
            public void receiveErrorget_lock_info(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for move method
            * override this method for handling normal response from move operation
            */
           public void receiveResultmove(
                    peerFile.wsdl.ServiceStub.MoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from move operation
           */
            public void receiveErrormove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for udate_content method
            * override this method for handling normal response from udate_content operation
            */
           public void receiveResultudate_content(
                    peerFile.wsdl.ServiceStub.Udate_contentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from udate_content operation
           */
            public void receiveErrorudate_content(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_configuration method
            * override this method for handling normal response from get_configuration operation
            */
           public void receiveResultget_configuration(
                    peerFile.wsdl.ServiceStub.Get_configurationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_configuration operation
           */
            public void receiveErrorget_configuration(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_entity_links_info method
            * override this method for handling normal response from get_entity_links_info operation
            */
           public void receiveResultget_entity_links_info(
                    peerFile.wsdl.ServiceStub.Get_entity_links_infoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_entity_links_info operation
           */
            public void receiveErrorget_entity_links_info(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fill_in_codes_in_path method
            * override this method for handling normal response from fill_in_codes_in_path operation
            */
           public void receiveResultfill_in_codes_in_path(
                    peerFile.wsdl.ServiceStub.Fill_in_codes_in_pathResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fill_in_codes_in_path operation
           */
            public void receiveErrorfill_in_codes_in_path(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for copy method
            * override this method for handling normal response from copy operation
            */
           public void receiveResultcopy(
                    peerFile.wsdl.ServiceStub.CopyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from copy operation
           */
            public void receiveErrorcopy(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_content method
            * override this method for handling normal response from get_content operation
            */
           public void receiveResultget_content(
                    peerFile.wsdl.ServiceStub.Get_contentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_content operation
           */
            public void receiveErrorget_content(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delete_entity method
            * override this method for handling normal response from delete_entity operation
            */
           public void receiveResultdelete_entity(
                    peerFile.wsdl.ServiceStub.Delete_entityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delete_entity operation
           */
            public void receiveErrordelete_entity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for logout method
            * override this method for handling normal response from logout operation
            */
           public void receiveResultlogout(
                    peerFile.wsdl.ServiceStub.LogoutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from logout operation
           */
            public void receiveErrorlogout(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlock_entity method
            * override this method for handling normal response from unlock_entity operation
            */
           public void receiveResultunlock_entity(
                    peerFile.wsdl.ServiceStub.Unlock_entityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlock_entity operation
           */
            public void receiveErrorunlock_entity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create_file method
            * override this method for handling normal response from create_file operation
            */
           public void receiveResultcreate_file(
                    peerFile.wsdl.ServiceStub.Create_fileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create_file operation
           */
            public void receiveErrorcreate_file(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create_entity_get_entity_class_list method
            * override this method for handling normal response from create_entity_get_entity_class_list operation
            */
           public void receiveResultcreate_entity_get_entity_class_list(
                    peerFile.wsdl.ServiceStub.Create_entity_get_entity_class_listResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create_entity_get_entity_class_list operation
           */
            public void receiveErrorcreate_entity_get_entity_class_list(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    peerFile.wsdl.ServiceStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for echo method
            * override this method for handling normal response from echo operation
            */
           public void receiveResultecho(
                    peerFile.wsdl.ServiceStub.EchoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from echo operation
           */
            public void receiveErrorecho(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_entity_name method
            * override this method for handling normal response from get_entity_name operation
            */
           public void receiveResultget_entity_name(
                    peerFile.wsdl.ServiceStub.Get_entity_nameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_entity_name operation
           */
            public void receiveErrorget_entity_name(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for set_entity_attributes method
            * override this method for handling normal response from set_entity_attributes operation
            */
           public void receiveResultset_entity_attributes(
                    peerFile.wsdl.ServiceStub.Set_entity_attributesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from set_entity_attributes operation
           */
            public void receiveErrorset_entity_attributes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exists_folder method
            * override this method for handling normal response from exists_folder operation
            */
           public void receiveResultexists_folder(
                    peerFile.wsdl.ServiceStub.Exists_folderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exists_folder operation
           */
            public void receiveErrorexists_folder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for get_user_locks method
            * override this method for handling normal response from get_user_locks operation
            */
           public void receiveResultget_user_locks(
                    peerFile.wsdl.ServiceStub.Get_user_locksResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from get_user_locks operation
           */
            public void receiveErrorget_user_locks(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for lock_entity method
            * override this method for handling normal response from lock_entity operation
            */
           public void receiveResultlock_entity(
                    peerFile.wsdl.ServiceStub.Lock_entityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from lock_entity operation
           */
            public void receiveErrorlock_entity(java.lang.Exception e) {
            }
                


    }
    