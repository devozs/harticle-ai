from harticle.utils import constants
import logging
LOGGER = logging.getLogger(__name__)


def check_debug():
    import os
    debug_param = os.environ.get(constants.ENV_DEBUG_WAIT_FOR_CLIENT)
    if debug_param is not None:
        LOGGER.info(f'debug_param: {debug_param}')
        wait_for_client_flag = int(debug_param)
        if wait_for_client_flag == 1:
            import debugpy
            debug_port = os.environ.get(constants.ENV_DEBUG_PORT)
            debugpy.listen(('0.0.0.0', int(debug_port)))
            LOGGER.info(f'DEBUG-MODE waiting in port 0.0.0.0:{debug_port} for client to be connected....')
            debugpy.wait_for_client()
        if wait_for_client_flag == 2:
            import pydevd_pycharm
            debug_port = os.environ.get(constants.ENV_DEBUG_PORT_PYCHARM)
            pydevd_pycharm.settrace('host.docker.internal', port=int(debug_port), stdoutToServer=True, stderrToServer=True)
            LOGGER.info(f'DEBUG-MODE waiting in port host.docker.internal:{debug_port} for client to be connected....')
